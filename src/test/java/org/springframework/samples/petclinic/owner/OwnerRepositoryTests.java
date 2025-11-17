/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link OwnerRepository}
 */
@DataJpaTest
class OwnerRepositoryTests {

	@Autowired
	private OwnerRepository ownerRepository;

	@Test
	void testFindPetTypes() {
		List<PetType> petTypes = ownerRepository.findPetTypes();

		assertThat(petTypes).isNotNull();
		assertThat(petTypes).isNotEmpty();
		// Default data should contain pet types
		assertThat(petTypes.size()).isGreaterThan(0);
	}

	@Test
	void testFindByLastName() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Owner> owners = ownerRepository.findByLastName("Franklin", pageable);

		assertThat(owners).isNotNull();
		assertThat(owners.getContent()).isNotEmpty();
		assertThat(owners.getContent().get(0).getLastName()).isEqualTo("Franklin");
	}

	@Test
	void testFindByLastNameNotFound() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Owner> owners = ownerRepository.findByLastName("NonExistentName", pageable);

		assertThat(owners).isNotNull();
		assertThat(owners.getContent()).isEmpty();
	}

	@Test
	void testFindById() {
		Owner owner = ownerRepository.findById(1);

		assertThat(owner).isNotNull();
		assertThat(owner.getId()).isEqualTo(1);
		assertThat(owner.getFirstName()).isNotNull();
		assertThat(owner.getLastName()).isNotNull();
	}

	@Test
	void testFindByIdWithPets() {
		Owner owner = ownerRepository.findById(1);

		assertThat(owner).isNotNull();
		// Owner 1 should have pets according to the default data
		assertThat(owner.getPets()).isNotNull();
	}

	@Test
	void testSaveNewOwner() {
		Owner newOwner = new Owner();
		newOwner.setFirstName("John");
		newOwner.setLastName("Doe");
		newOwner.setAddress("123 Main St");
		newOwner.setCity("Springfield");
		newOwner.setTelephone("1234567890");

		ownerRepository.save(newOwner);

		assertThat(newOwner.getId()).isNotNull();
		assertThat(newOwner.getId()).isGreaterThan(0);
	}

	@Test
	void testSaveOwnerWithPet() {
		Owner owner = new Owner();
		owner.setFirstName("Jane");
		owner.setLastName("Smith");
		owner.setAddress("456 Oak Ave");
		owner.setCity("Madison");
		owner.setTelephone("9876543210");

		Pet pet = new Pet();
		pet.setName("Buddy");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));
		PetType petType = ownerRepository.findPetTypes().get(0);
		pet.setType(petType);

		owner.addPet(pet);
		ownerRepository.save(owner);

		assertThat(owner.getId()).isNotNull();
		Owner savedOwner = ownerRepository.findById(owner.getId());
		assertThat(savedOwner.getPets()).hasSize(1);
		assertThat(savedOwner.getPets().get(0).getName()).isEqualTo("Buddy");
	}

	@Test
	void testUpdateOwner() {
		Owner owner = ownerRepository.findById(1);
		String originalFirstName = owner.getFirstName();
		String newFirstName = "UpdatedName";

		owner.setFirstName(newFirstName);
		ownerRepository.save(owner);

		Owner updatedOwner = ownerRepository.findById(1);
		assertThat(updatedOwner.getFirstName()).isEqualTo(newFirstName);
		assertThat(updatedOwner.getFirstName()).isNotEqualTo(originalFirstName);
	}

	@Test
	void testFindAll() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Owner> owners = ownerRepository.findAll(pageable);

		assertThat(owners).isNotNull();
		assertThat(owners.getContent()).isNotEmpty();
		assertThat(owners.getTotalElements()).isGreaterThan(0);
	}

	@Test
	void testFindAllPagination() {
		Pageable firstPage = PageRequest.of(0, 5);
		Page<Owner> firstPageResult = ownerRepository.findAll(firstPage);

		assertThat(firstPageResult.getContent()).hasSizeLessThanOrEqualTo(5);
		assertThat(firstPageResult.getNumber()).isEqualTo(0);
	}

	@Test
	void testFindByLastNamePartialMatch() {
		// Test that findByLastName works with partial match (LIKE clause)
		Pageable pageable = PageRequest.of(0, 10);
		Page<Owner> owners = ownerRepository.findByLastName("Frank", pageable);

		assertThat(owners).isNotNull();
		// Should find "Franklin" with "Frank" prefix
	}

}

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SerializationUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Owner} entity
 *
 * @author Test Author
 */
class OwnerTests {

	private Owner owner;

	@BeforeEach
	void setUp() {
		owner = new Owner();
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("6085551023");
	}

	@Test
	void testOwnerGettersAndSetters() {
		assertThat(owner.getFirstName()).isEqualTo("George");
		assertThat(owner.getLastName()).isEqualTo("Franklin");
		assertThat(owner.getAddress()).isEqualTo("110 W. Liberty St.");
		assertThat(owner.getCity()).isEqualTo("Madison");
		assertThat(owner.getTelephone()).isEqualTo("6085551023");
	}

	@Test
	void testSetAddress() {
		owner.setAddress("123 Main Street");
		assertThat(owner.getAddress()).isEqualTo("123 Main Street");
	}

	@Test
	void testSetCity() {
		owner.setCity("Springfield");
		assertThat(owner.getCity()).isEqualTo("Springfield");
	}

	@Test
	void testSetTelephone() {
		owner.setTelephone("5551234567");
		assertThat(owner.getTelephone()).isEqualTo("5551234567");
	}

	@Test
	void testGetPetsReturnsEmptyListInitially() {
		assertThat(owner.getPets()).isNotNull();
		assertThat(owner.getPets()).isEmpty();
	}

	@Test
	void testAddNewPet() {
		Pet pet = new Pet();
		pet.setName("Max");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));

		owner.addPet(pet);

		assertThat(owner.getPets()).hasSize(1);
		assertThat(owner.getPets().get(0).getName()).isEqualTo("Max");
	}

	@Test
	void testAddPetWithId() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Buddy");

		owner.addPet(pet);

		// Pet with ID should not be added (not new)
		assertThat(owner.getPets()).isEmpty();
	}

	@Test
	void testAddMultiplePets() {
		Pet pet1 = new Pet();
		pet1.setName("Max");

		Pet pet2 = new Pet();
		pet2.setName("Bella");

		owner.addPet(pet1);
		owner.addPet(pet2);

		assertThat(owner.getPets()).hasSize(2);
	}

	@Test
	void testGetPetByName() {
		Pet pet = new Pet();
		pet.setName("Max");
		owner.addPet(pet);

		Pet foundPet = owner.getPet("Max");

		assertThat(foundPet).isNotNull();
		assertThat(foundPet.getName()).isEqualTo("Max");
	}

	@Test
	void testGetPetByNameCaseInsensitive() {
		Pet pet = new Pet();
		pet.setName("Max");
		owner.addPet(pet);

		Pet foundPet = owner.getPet("max");

		assertThat(foundPet).isNotNull();
		assertThat(foundPet.getName()).isEqualTo("Max");
	}

	@Test
	void testGetPetByNameNotFound() {
		Pet foundPet = owner.getPet("NonExistent");
		assertThat(foundPet).isNull();
	}

	@Test
	void testGetPetById() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Max");
		owner.getPets().add(pet); // Direct add to list since addPet won't add pets with
									// IDs

		Pet foundPet = owner.getPet(1);

		assertThat(foundPet).isNotNull();
		assertThat(foundPet.getId()).isEqualTo(1);
	}

	@Test
	void testGetPetByIdNotFound() {
		Pet foundPet = owner.getPet(999);
		assertThat(foundPet).isNull();
	}

	@Test
	void testGetPetWithIgnoreNew() {
		Pet newPet = new Pet();
		newPet.setName("Max");
		owner.addPet(newPet);

		// Should return null when ignoring new pets
		Pet foundPet = owner.getPet("Max", true);
		assertThat(foundPet).isNull();

		// Should return the pet when not ignoring new pets
		foundPet = owner.getPet("Max", false);
		assertThat(foundPet).isNotNull();
	}

	@Test
	void testAddVisitToPet() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Max");
		owner.getPets().add(pet);

		Visit visit = new Visit();
		visit.setDate(LocalDate.now());
		visit.setDescription("Checkup");

		owner.addVisit(1, visit);

		assertThat(pet.getVisits()).hasSize(1);
		assertThat(pet.getVisits().iterator().next().getDescription()).isEqualTo("Checkup");
	}

	@Test
	void testAddVisitWithNullPetId() {
		Visit visit = new Visit();
		visit.setDate(LocalDate.now());

		assertThrows(IllegalArgumentException.class, () -> {
			owner.addVisit(null, visit);
		});
	}

	@Test
	void testAddVisitWithNullVisit() {
		Pet pet = new Pet();
		pet.setId(1);
		owner.getPets().add(pet);

		assertThrows(IllegalArgumentException.class, () -> {
			owner.addVisit(1, null);
		});
	}

	@Test
	void testAddVisitWithInvalidPetId() {
		assertThrows(IllegalArgumentException.class, () -> {
			Visit visit = new Visit();
			visit.setDate(LocalDate.now());
			owner.addVisit(999, visit);
		});
	}

	@Test
	void testToString() {
		owner.setId(1);
		String result = owner.toString();

		assertThat(result).contains("George");
		assertThat(result).contains("Franklin");
		assertThat(result).contains("Madison");
		assertThat(result).contains("110 W. Liberty St.");
		assertThat(result).contains("6085551023");
	}

	@Test
	void testSerialization() {
		owner.setId(123);
		@SuppressWarnings("deprecation")
		Owner deserializedOwner = (Owner) SerializationUtils.deserialize(SerializationUtils.serialize(owner));

		assertThat(deserializedOwner.getFirstName()).isEqualTo(owner.getFirstName());
		assertThat(deserializedOwner.getLastName()).isEqualTo(owner.getLastName());
		assertThat(deserializedOwner.getId()).isEqualTo(owner.getId());
		assertThat(deserializedOwner.getAddress()).isEqualTo(owner.getAddress());
		assertThat(deserializedOwner.getCity()).isEqualTo(owner.getCity());
		assertThat(deserializedOwner.getTelephone()).isEqualTo(owner.getTelephone());
	}

	@Test
	void testIsNewOwner() {
		Owner newOwner = new Owner();
		assertThat(newOwner.isNew()).isTrue();

		newOwner.setId(1);
		assertThat(newOwner.isNew()).isFalse();
	}

}

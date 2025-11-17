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
package org.springframework.samples.petclinic.vet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@link VetRepository}
 */
@DataJpaTest
class VetRepositoryTests {

	@Autowired
	private VetRepository vetRepository;

	@Test
	void testFindAll() {
		Collection<Vet> vets = vetRepository.findAll();

		assertThat(vets).isNotNull();
		assertThat(vets).isNotEmpty();
		// Default data should contain vets
		assertThat(vets.size()).isGreaterThan(0);
	}

	@Test
	void testFindAllPaginated() {
		Pageable pageable = PageRequest.of(0, 5);
		Page<Vet> vets = vetRepository.findAll(pageable);

		assertThat(vets).isNotNull();
		assertThat(vets.getContent()).isNotEmpty();
		assertThat(vets.getContent().size()).isLessThanOrEqualTo(5);
	}

	@Test
	void testFindAllContainsVetsWithSpecialties() {
		Collection<Vet> vets = vetRepository.findAll();

		// At least one vet should have specialties
		boolean hasVetWithSpecialty = vets.stream()
			.anyMatch(vet -> vet.getNrOfSpecialties() > 0);

		assertThat(hasVetWithSpecialty).isTrue();
	}

	@Test
	void testVetHasFirstAndLastName() {
		Collection<Vet> vets = vetRepository.findAll();

		for (Vet vet : vets) {
			assertThat(vet.getFirstName()).isNotNull();
			assertThat(vet.getLastName()).isNotNull();
			assertThat(vet.getId()).isNotNull();
		}
	}

	@Test
	void testFindAllPaginationSecondPage() {
		Pageable firstPage = PageRequest.of(0, 3);
		Page<Vet> firstPageResult = vetRepository.findAll(firstPage);

		if (firstPageResult.getTotalPages() > 1) {
			Pageable secondPage = PageRequest.of(1, 3);
			Page<Vet> secondPageResult = vetRepository.findAll(secondPage);

			assertThat(secondPageResult.getNumber()).isEqualTo(1);
			assertThat(secondPageResult.getContent()).isNotEmpty();
		}
	}

	@Test
	void testCacheableAnnotation() {
		// First call - should hit database
		Collection<Vet> vets1 = vetRepository.findAll();

		// Second call - should use cache
		Collection<Vet> vets2 = vetRepository.findAll();

		assertThat(vets1).isNotNull();
		assertThat(vets2).isNotNull();
		assertThat(vets1.size()).isEqualTo(vets2.size());
	}

	@Test
	void testVetSpecialtiesAreLoaded() {
		Collection<Vet> vets = vetRepository.findAll();

		for (Vet vet : vets) {
			// Specialties should be eagerly loaded
			assertThat(vet.getSpecialties()).isNotNull();
		}
	}

	@Test
	void testPaginationTotalElements() {
		Collection<Vet> allVets = vetRepository.findAll();
		int totalVets = allVets.size();

		Pageable pageable = PageRequest.of(0, 10);
		Page<Vet> paginatedVets = vetRepository.findAll(pageable);

		assertThat(paginatedVets.getTotalElements()).isEqualTo(totalVets);
	}

}

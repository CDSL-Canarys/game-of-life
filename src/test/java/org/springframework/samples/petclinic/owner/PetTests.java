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

/**
 * Unit tests for the {@link Pet} entity
 */
class PetTests {

	private Pet pet;

	private PetType petType;

	@BeforeEach
	void setUp() {
		pet = new Pet();
		petType = new PetType();
		petType.setName("dog");
	}

	@Test
	void testSetAndGetName() {
		pet.setName("Max");
		assertThat(pet.getName()).isEqualTo("Max");
	}

	@Test
	void testSetAndGetBirthDate() {
		LocalDate birthDate = LocalDate.of(2020, 1, 15);
		pet.setBirthDate(birthDate);
		assertThat(pet.getBirthDate()).isEqualTo(birthDate);
	}

	@Test
	void testSetAndGetType() {
		pet.setType(petType);
		assertThat(pet.getType()).isEqualTo(petType);
		assertThat(pet.getType().getName()).isEqualTo("dog");
	}

	@Test
	void testGetVisitsReturnsEmptyCollectionInitially() {
		assertThat(pet.getVisits()).isNotNull();
		assertThat(pet.getVisits()).isEmpty();
	}

	@Test
	void testAddVisit() {
		Visit visit = new Visit();
		visit.setDate(LocalDate.now());
		visit.setDescription("Checkup");

		pet.addVisit(visit);

		assertThat(pet.getVisits()).hasSize(1);
		assertThat(pet.getVisits().iterator().next().getDescription()).isEqualTo("Checkup");
	}

	@Test
	void testAddMultipleVisits() {
		Visit visit1 = new Visit();
		visit1.setDate(LocalDate.of(2023, 1, 1));
		visit1.setDescription("Checkup");

		Visit visit2 = new Visit();
		visit2.setDate(LocalDate.of(2023, 6, 1));
		visit2.setDescription("Vaccination");

		pet.addVisit(visit1);
		pet.addVisit(visit2);

		assertThat(pet.getVisits()).hasSize(2);
	}

	@Test
	void testIsNewPet() {
		assertThat(pet.isNew()).isTrue();

		pet.setId(1);
		assertThat(pet.isNew()).isFalse();
	}

	@Test
	void testPetWithId() {
		pet.setId(42);
		assertThat(pet.getId()).isEqualTo(42);
		assertThat(pet.isNew()).isFalse();
	}

	@Test
	void testPetInheritsFromNamedEntity() {
		pet.setName("Buddy");
		assertThat(pet.getName()).isEqualTo("Buddy");
	}

	@Test
	void testSerialization() {
		pet.setId(123);
		pet.setName("Max");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));
		pet.setType(petType);

		@SuppressWarnings("deprecation")
		Pet deserializedPet = (Pet) SerializationUtils.deserialize(SerializationUtils.serialize(pet));

		assertThat(deserializedPet.getId()).isEqualTo(pet.getId());
		assertThat(deserializedPet.getName()).isEqualTo(pet.getName());
		assertThat(deserializedPet.getBirthDate()).isEqualTo(pet.getBirthDate());
	}

	@Test
	void testPetTypeChange() {
		PetType cat = new PetType();
		cat.setName("cat");

		pet.setType(petType);
		assertThat(pet.getType().getName()).isEqualTo("dog");

		pet.setType(cat);
		assertThat(pet.getType().getName()).isEqualTo("cat");
	}

	@Test
	void testBirthDateChange() {
		LocalDate date1 = LocalDate.of(2020, 1, 1);
		LocalDate date2 = LocalDate.of(2021, 1, 1);

		pet.setBirthDate(date1);
		assertThat(pet.getBirthDate()).isEqualTo(date1);

		pet.setBirthDate(date2);
		assertThat(pet.getBirthDate()).isEqualTo(date2);
	}

	@Test
	void testVisitCollection() {
		Visit visit1 = new Visit();
		visit1.setId(1);
		visit1.setDate(LocalDate.of(2023, 1, 1));

		Visit visit2 = new Visit();
		visit2.setId(2);
		visit2.setDate(LocalDate.of(2023, 2, 1));

		pet.addVisit(visit1);
		pet.addVisit(visit2);

		assertThat(pet.getVisits()).contains(visit1, visit2);
	}

}

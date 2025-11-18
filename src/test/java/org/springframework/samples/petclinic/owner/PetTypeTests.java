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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link PetType} entity
 */
class PetTypeTests {

	private PetType petType;

	@BeforeEach
	void setUp() {
		petType = new PetType();
	}

	@Test
	void testSetAndGetName() {
		petType.setName("dog");
		assertThat(petType.getName()).isEqualTo("dog");
	}

	@Test
	void testNameIsNullInitially() {
		assertThat(petType.getName()).isNull();
	}

	@Test
	void testNameCanBeChanged() {
		petType.setName("cat");
		assertThat(petType.getName()).isEqualTo("cat");

		petType.setName("bird");
		assertThat(petType.getName()).isEqualTo("bird");
	}

	@Test
	void testPetTypeInheritsFromNamedEntity() {
		petType.setName("hamster");
		petType.setId(1);

		assertThat(petType.getName()).isEqualTo("hamster");
		assertThat(petType.getId()).isEqualTo(1);
	}

	@Test
	void testIsNewPetType() {
		assertThat(petType.isNew()).isTrue();

		petType.setId(1);
		assertThat(petType.isNew()).isFalse();
	}

	@Test
	void testMultiplePetTypes() {
		PetType dog = new PetType();
		dog.setName("dog");
		dog.setId(1);

		PetType cat = new PetType();
		cat.setName("cat");
		cat.setId(2);

		assertThat(dog.getName()).isEqualTo("dog");
		assertThat(cat.getName()).isEqualTo("cat");
		assertThat(dog.getId()).isNotEqualTo(cat.getId());
	}

	@Test
	void testPetTypeWithCommonNames() {
		String[] commonPetTypes = { "dog", "cat", "bird", "hamster", "lizard", "snake" };

		for (String type : commonPetTypes) {
			PetType pt = new PetType();
			pt.setName(type);
			assertThat(pt.getName()).isEqualTo(type);
		}
	}

}

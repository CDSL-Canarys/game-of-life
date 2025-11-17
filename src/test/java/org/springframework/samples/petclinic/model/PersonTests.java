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
package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link Person} entity
 */
class PersonTests {

	private Person person;

	// Create a concrete implementation of Person for testing since it's abstract (MappedSuperclass)
	private static class TestPerson extends Person {
		// No additional implementation needed
	}

	@BeforeEach
	void setUp() {
		person = new TestPerson();
	}

	@Test
	void testSetAndGetFirstName() {
		person.setFirstName("John");
		assertThat(person.getFirstName()).isEqualTo("John");
	}

	@Test
	void testSetAndGetLastName() {
		person.setLastName("Doe");
		assertThat(person.getLastName()).isEqualTo("Doe");
	}

	@Test
	void testFirstNameCanBeChanged() {
		person.setFirstName("John");
		person.setFirstName("Jane");
		assertThat(person.getFirstName()).isEqualTo("Jane");
	}

	@Test
	void testLastNameCanBeChanged() {
		person.setLastName("Doe");
		person.setLastName("Smith");
		assertThat(person.getLastName()).isEqualTo("Smith");
	}

	@Test
	void testPersonWithNullNames() {
		assertThat(person.getFirstName()).isNull();
		assertThat(person.getLastName()).isNull();
	}

	@Test
	void testPersonInheritsFromBaseEntity() {
		assertThat(person).isInstanceOf(BaseEntity.class);
	}

	@Test
	void testPersonHasIdFromBaseEntity() {
		person.setId(1);
		assertThat(person.getId()).isEqualTo(1);
	}

	@Test
	void testIsNewFromBaseEntity() {
		assertThat(person.isNew()).isTrue();
		person.setId(1);
		assertThat(person.isNew()).isFalse();
	}

}

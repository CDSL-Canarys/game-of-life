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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link Specialty} entity
 */
class SpecialtyTests {

	private Specialty specialty;

	@BeforeEach
	void setUp() {
		specialty = new Specialty();
	}

	@Test
	void testSetAndGetName() {
		specialty.setName("radiology");
		assertThat(specialty.getName()).isEqualTo("radiology");
	}

	@Test
	void testNameIsNullInitially() {
		assertThat(specialty.getName()).isNull();
	}

	@Test
	void testNameCanBeChanged() {
		specialty.setName("surgery");
		assertThat(specialty.getName()).isEqualTo("surgery");

		specialty.setName("dentistry");
		assertThat(specialty.getName()).isEqualTo("dentistry");
	}

	@Test
	void testSpecialtyInheritsFromNamedEntity() {
		specialty.setName("cardiology");
		specialty.setId(1);

		assertThat(specialty.getName()).isEqualTo("cardiology");
		assertThat(specialty.getId()).isEqualTo(1);
	}

	@Test
	void testIsNewSpecialty() {
		assertThat(specialty.isNew()).isTrue();

		specialty.setId(1);
		assertThat(specialty.isNew()).isFalse();
	}

	@Test
	void testMultipleSpecialties() {
		Specialty radiology = new Specialty();
		radiology.setName("radiology");
		radiology.setId(1);

		Specialty surgery = new Specialty();
		surgery.setName("surgery");
		surgery.setId(2);

		assertThat(radiology.getName()).isEqualTo("radiology");
		assertThat(surgery.getName()).isEqualTo("surgery");
		assertThat(radiology.getId()).isNotEqualTo(surgery.getId());
	}

	@Test
	void testCommonSpecialties() {
		String[] commonSpecialties = {"radiology", "surgery", "dentistry", "cardiology", "dermatology"};

		for (String spec : commonSpecialties) {
			Specialty s = new Specialty();
			s.setName(spec);
			assertThat(s.getName()).isEqualTo(spec);
		}
	}

	@Test
	void testSpecialtyInheritsFromBaseEntity() {
		assertThat(specialty).isInstanceOf(org.springframework.samples.petclinic.model.BaseEntity.class);
		assertThat(specialty).isInstanceOf(org.springframework.samples.petclinic.model.NamedEntity.class);
	}

}

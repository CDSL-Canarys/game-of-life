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
import org.springframework.util.SerializationUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dave Syer
 */
class VetTests {

	private Vet vet;

	@BeforeEach
	void setUp() {
		vet = new Vet();
		vet.setFirstName("James");
		vet.setLastName("Carter");
		vet.setId(1);
	}

	@Test
	void testSerialization() {
		Vet vet = new Vet();
		vet.setFirstName("Zaphod");
		vet.setLastName("Beeblebrox");
		vet.setId(123);
		@SuppressWarnings("deprecation")
		Vet other = (Vet) SerializationUtils.deserialize(SerializationUtils.serialize(vet));
		assertThat(other.getFirstName()).isEqualTo(vet.getFirstName());
		assertThat(other.getLastName()).isEqualTo(vet.getLastName());
		assertThat(other.getId()).isEqualTo(vet.getId());
	}

	@Test
	void testGetSpecialtiesReturnsEmptyListInitially() {
		assertThat(vet.getSpecialties()).isNotNull();
		assertThat(vet.getSpecialties()).isEmpty();
	}

	@Test
	void testGetNrOfSpecialtiesReturnsZeroInitially() {
		assertThat(vet.getNrOfSpecialties()).isEqualTo(0);
	}

	@Test
	void testAddSpecialty() {
		Specialty radiology = new Specialty();
		radiology.setName("radiology");
		radiology.setId(1);

		vet.addSpecialty(radiology);

		assertThat(vet.getSpecialties()).hasSize(1);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
		assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("radiology");
	}

	@Test
	void testAddMultipleSpecialties() {
		Specialty radiology = new Specialty();
		radiology.setName("radiology");
		radiology.setId(1);

		Specialty surgery = new Specialty();
		surgery.setName("surgery");
		surgery.setId(2);

		vet.addSpecialty(radiology);
		vet.addSpecialty(surgery);

		assertThat(vet.getSpecialties()).hasSize(2);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
	}

	@Test
	void testSpecialtiesAreSorted() {
		Specialty surgery = new Specialty();
		surgery.setName("surgery");
		surgery.setId(2);

		Specialty dentistry = new Specialty();
		dentistry.setName("dentistry");
		dentistry.setId(1);

		Specialty radiology = new Specialty();
		radiology.setName("radiology");
		radiology.setId(3);

		// Add in non-alphabetical order
		vet.addSpecialty(surgery);
		vet.addSpecialty(dentistry);
		vet.addSpecialty(radiology);

		// Should be sorted alphabetically
		assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("dentistry");
		assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("radiology");
		assertThat(vet.getSpecialties().get(2).getName()).isEqualTo("surgery");
	}

	@Test
	void testGetSpecialtiesReturnsUnmodifiableList() {
		Specialty radiology = new Specialty();
		radiology.setName("radiology");
		vet.addSpecialty(radiology);

		assertThat(vet.getSpecialties()).isUnmodifiable();
	}

	@Test
	void testVetInheritsFromPerson() {
		assertThat(vet.getFirstName()).isEqualTo("James");
		assertThat(vet.getLastName()).isEqualTo("Carter");
	}

	@Test
	void testVetGettersAndSetters() {
		Vet newVet = new Vet();
		newVet.setFirstName("Helen");
		newVet.setLastName("Leary");
		newVet.setId(2);

		assertThat(newVet.getFirstName()).isEqualTo("Helen");
		assertThat(newVet.getLastName()).isEqualTo("Leary");
		assertThat(newVet.getId()).isEqualTo(2);
	}

	@Test
	void testIsNewVet() {
		Vet newVet = new Vet();
		assertThat(newVet.isNew()).isTrue();

		newVet.setId(1);
		assertThat(newVet.isNew()).isFalse();
	}

	@Test
	void testAddDuplicateSpecialty() {
		Specialty radiology = new Specialty();
		radiology.setName("radiology");
		radiology.setId(1);

		vet.addSpecialty(radiology);
		vet.addSpecialty(radiology); // Add same specialty again

		// Set should not contain duplicates
		assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
	}

}

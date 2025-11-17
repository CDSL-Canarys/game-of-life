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

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link Visit} entity
 */
class VisitTests {

	private Visit visit;

	@BeforeEach
	void setUp() {
		visit = new Visit();
	}

	@Test
	void testSetAndGetDate() {
		LocalDate date = LocalDate.of(2023, 6, 15);
		visit.setDate(date);
		assertThat(visit.getDate()).isEqualTo(date);
	}

	@Test
	void testSetAndGetDescription() {
		visit.setDescription("Annual checkup");
		assertThat(visit.getDescription()).isEqualTo("Annual checkup");
	}

	@Test
	void testDateIsNullInitially() {
		assertThat(visit.getDate()).isNull();
	}

	@Test
	void testDescriptionIsNullInitially() {
		assertThat(visit.getDescription()).isNull();
	}

	@Test
	void testVisitWithMultipleChanges() {
		LocalDate date1 = LocalDate.of(2023, 1, 1);
		LocalDate date2 = LocalDate.of(2023, 6, 1);

		visit.setDate(date1);
		visit.setDescription("First visit");

		assertThat(visit.getDate()).isEqualTo(date1);
		assertThat(visit.getDescription()).isEqualTo("First visit");

		visit.setDate(date2);
		visit.setDescription("Follow-up visit");

		assertThat(visit.getDate()).isEqualTo(date2);
		assertThat(visit.getDescription()).isEqualTo("Follow-up visit");
	}

	@Test
	void testIsNewVisit() {
		assertThat(visit.isNew()).isTrue();

		visit.setId(1);
		assertThat(visit.isNew()).isFalse();
	}

	@Test
	void testVisitInheritsFromBaseEntity() {
		visit.setId(42);
		assertThat(visit.getId()).isEqualTo(42);
	}

	@Test
	void testDescriptionCanBeEmpty() {
		visit.setDescription("");
		assertThat(visit.getDescription()).isEmpty();
	}

	@Test
	void testDescriptionCanBeLong() {
		String longDescription = "This is a very long description that contains many details about the visit " +
			"including symptoms, diagnosis, treatment plan, and follow-up recommendations.";
		visit.setDescription(longDescription);
		assertThat(visit.getDescription()).isEqualTo(longDescription);
	}

	@Test
	void testVisitWithCurrentDate() {
		LocalDate today = LocalDate.now();
		visit.setDate(today);
		assertThat(visit.getDate()).isEqualTo(today);
	}

	@Test
	void testVisitWithPastDate() {
		LocalDate pastDate = LocalDate.of(2020, 1, 1);
		visit.setDate(pastDate);
		assertThat(visit.getDate()).isBefore(LocalDate.now());
	}

}

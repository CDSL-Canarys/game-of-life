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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link BaseEntity}
 */
class BaseEntityTests {

	// Create a concrete implementation for testing
	private static class TestEntity extends BaseEntity {

	}

	@Test
	void testIdIsNullInitially() {
		BaseEntity entity = new TestEntity();
		assertThat(entity.getId()).isNull();
	}

	@Test
	void testSetAndGetId() {
		BaseEntity entity = new TestEntity();
		entity.setId(42);
		assertThat(entity.getId()).isEqualTo(42);
	}

	@Test
	void testIsNewReturnsTrueWhenIdIsNull() {
		BaseEntity entity = new TestEntity();
		assertThat(entity.isNew()).isTrue();
	}

	@Test
	void testIsNewReturnsFalseWhenIdIsSet() {
		BaseEntity entity = new TestEntity();
		entity.setId(1);
		assertThat(entity.isNew()).isFalse();
	}

	@Test
	void testIdCanBeChanged() {
		BaseEntity entity = new TestEntity();
		entity.setId(1);
		assertThat(entity.getId()).isEqualTo(1);

		entity.setId(2);
		assertThat(entity.getId()).isEqualTo(2);
	}

	@Test
	void testMultipleEntitiesHaveIndependentIds() {
		BaseEntity entity1 = new TestEntity();
		BaseEntity entity2 = new TestEntity();

		entity1.setId(1);
		entity2.setId(2);

		assertThat(entity1.getId()).isEqualTo(1);
		assertThat(entity2.getId()).isEqualTo(2);
		assertThat(entity1.getId()).isNotEqualTo(entity2.getId());
	}

}

/*
 * Copyright (C) 2024 Lokesh Bisht
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package dev.lokeshbisht.GenreService.repository;

import dev.lokeshbisht.GenreService.entity.Genre;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    private static final List<Genre> genreList = new ArrayList<>();

    @BeforeAll
    static void generateGenreList() {

        Genre genre = new Genre(8L, "Fantasy", "Inu", null, "Uttara", null);
        Genre genre2 = new Genre(11L, "Documentary", "Pururava ", null, "Nahush", null);
        Genre genre3 = new Genre(14L, "Romance", "Ayu", null, "Ira", null);
        genreList.add(genre);
        genreList.add(genre2);
        genreList.add(genre3);
    }

    @Test
    void testSaveGenre() {
        Genre genre = new Genre();
        genre.setName("Animated");
        genre.setCreatedBy("lokeshbisht");
        genre.setCreatedAt(new Date());

        Genre savedEntity = genreRepository.save(genre);
        Genre retrievedEntity = genreRepository.findById(savedEntity.getId()).orElse(null);
        assertNotNull(retrievedEntity);
        assertEquals("Animated", retrievedEntity.getName());
        assertEquals(genre.getCreatedAt(), retrievedEntity.getCreatedAt());
    }

    @Test
    void updateExistingGenreTest() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Action");
        genre.setCreatedBy("lokeshbisht");
        genre.setCreatedAt(new Date());

        Genre savedEntity = genreRepository.save(genre);
        Genre updatedGenreEntity = genreRepository.findById(savedEntity.getId()).orElse(null);
        assertNotNull(updatedGenreEntity);
        assertEquals("Action", updatedGenreEntity.getName());

        updatedGenreEntity.setName("Comedy");
        updatedGenreEntity.setUpdatedBy("som");
        updatedGenreEntity.setUpdatedAt(new Date());
        Genre retrievedEntity = genreRepository.save(updatedGenreEntity);

        assertNotNull(retrievedEntity);
        assertEquals(1, retrievedEntity.getId());
        assertEquals("Comedy", retrievedEntity.getName());
        assertEquals("lokeshbisht", retrievedEntity.getCreatedBy());
        assertEquals("som", retrievedEntity.getUpdatedBy());
    }

//    @Test
//    void updateInvalidGenreTest() {
//        Genre genre = genreRepository.findById(400L).orElse(null);
//        assertNull(genre);
//    }

    @Test
    void getGenreByGenreId() {
        Long genreId = genreRepository.save(genreList.get(0)).getId();
        Genre retrievedEntity = genreRepository.findById(genreId).orElse(null);
        assertNotNull(retrievedEntity);
        assertEquals(genreId, retrievedEntity.getId());
        assertEquals(genreList.get(0).getName(), retrievedEntity.getName());
        assertEquals(genreList.get(0).getCreatedBy(), retrievedEntity.getCreatedBy());
        assertEquals(genreList.get(0).getUpdatedBy(), retrievedEntity.getUpdatedBy());
    }
}

package test.idealista.infrastructure.repositories;

import com.idealista.infrastructure.repositories.AdRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdRepositoryTest {

    private AdRepository adRepository;

    @Test
    @DisplayName("should return the list of picture ids for a given ad id")
    void shouldReturnListOfPictureIdForAnAdId() {
        adRepository = new AdRepository();
        assertEquals(Arrays.asList(3,8),adRepository.getPictureIdListById(5));
        assertEquals(Arrays.asList(6),adRepository.getPictureIdListById(6));
    }
}

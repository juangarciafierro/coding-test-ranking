package test.idealista.infrastructure.repositories;

import com.idealista.infrastructure.repositories.PictureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PictureRepositoryTest {

    private PictureRepository pictureRepository;

    private static final String SD = "SD";
    private static final String HD = "HD";
    private static final String BASE_URL = "http://www.idealista.com/pictures/";
    private static final String URL1 = BASE_URL + "1";
    private static final String URL2 = BASE_URL + "2";

    @Test
    @DisplayName("should return the quality of a given picture URL")
    void shouldReturnQualityOfAGivenPictureUrl() {
        pictureRepository = new PictureRepository();
        assertEquals(SD,pictureRepository.getPictureQualityByUrl(URL1));
        assertEquals(HD,pictureRepository.getPictureQualityByUrl(URL2));
    }

    @Test
    @DisplayName("should return the id of the photo of a given url")
    void shouldReturnIdForAGivenUrl() {
        pictureRepository = new PictureRepository();
        assertEquals(URL1,pictureRepository.getPictureUrlById(1));
        assertEquals(URL2,pictureRepository.getPictureUrlById(2));
    }
}

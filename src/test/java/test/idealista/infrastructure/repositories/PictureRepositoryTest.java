package test.idealista.infrastructure.repositories;

import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.infrastructure.persistence.PictureVO;
import com.idealista.application.repositories.PictureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PictureRepositoryTest {

    @InjectMocks
    PictureRepository pictureRepository;

    @Mock
    InMemoryPersistence dataBase;

    private static final String SD = "SD";
    private static final String HD = "HD";
    private static final String BASE_URL = "http://photos/";
    private static final String URL1 = BASE_URL + "1";
    private static final String URL2 = BASE_URL + "2";

    @Test
    @DisplayName("should return the quality of a given picture URL")
    void shouldReturnQualityOfAGivenPictureUrl() {
        ArrayList<PictureVO> pictures = new ArrayList<>();
        pictures.add(new PictureVO(1, URL1, SD));
        pictures.add(new PictureVO(2, URL2, HD));
        when(dataBase.getPictures()).thenReturn(pictures);
        assertEquals(SD,pictureRepository.getPictureQualityByUrl(URL1));
        assertEquals(HD,pictureRepository.getPictureQualityByUrl(URL2));
    }

    @Test
    @DisplayName("should return the id of the photo of a given url")
    void shouldReturnIdForAGivenUrl() {
        ArrayList<PictureVO> pictures = new ArrayList<>();
        pictures.add(new PictureVO(1, URL1, SD));
        pictures.add(new PictureVO(2, URL2, HD));
        when(dataBase.getPictures()).thenReturn(pictures);
        assertEquals(URL1,pictureRepository.getPictureUrlById(1));
        assertEquals(URL2,pictureRepository.getPictureUrlById(2));
    }
}

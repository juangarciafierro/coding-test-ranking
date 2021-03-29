package test.idealista.infrastructure.repositories;

import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.application.repositories.AdRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdRepositoryTest {

    @InjectMocks
    AdRepository adRepository;

    @Mock
    InMemoryPersistence dataBase;

    @Test
    @DisplayName("should return the list of picture ids for a given ad id")
    void shouldReturnListOfPictureIdForAnAdId() {
        ArrayList<AdVO> ads = new ArrayList<>();
        AdVO ad1 = new AdVO(1, null, null, Arrays.asList(1,2), null, null, null, null);
        AdVO ad2 = new AdVO(2, null, null, Arrays.asList(3), null, null, null, null);
        ads.add(ad1);
        ads.add(ad2);
        when(dataBase.getAds()).thenReturn(ads);
        doReturn(ad1).when(dataBase).getAdById(1);
        doReturn(ad2).when(dataBase).getAdById(2);
        assertEquals(Arrays.asList(1,2),adRepository.getPictureIdListById(1));
        assertEquals(Arrays.asList(3),adRepository.getPictureIdListById(2));
    }
}

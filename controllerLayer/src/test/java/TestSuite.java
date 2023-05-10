import com.blackburn.DTO.CatDTO;
import com.blackburn.DTO.CatOwnerDTO;
import com.blackburn.DTO.ColorDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestSuite {
    private final CatOwnerDTO makambombich;

    private final CatOwnerDTO dverM;

    private final CatDTO shoka;

    private final CatDTO chillie;

    private final CatDTO taylor;

    private final ColorDTO whateverColor;
}

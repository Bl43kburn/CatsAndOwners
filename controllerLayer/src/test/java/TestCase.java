import com.blackburn.App;
import com.blackburn.DTO.CatDTO;
import com.blackburn.DTO.CatOwnerDTO;
import com.blackburn.DTO.ColorDTO;
import com.blackburn.DTO.TransferRequestDTO;
import com.blackburn.exceptions.FriendshipException;
import com.blackburn.exceptions.OwnershipException;
import com.blackburn.exceptions.SearchException;
import com.blackburn.services.*;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.mockito.Mockito.mockStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = App.class)
@PropertySource("classpath:")
public class TestCase {

    private final ColorService colorService;

    private final CatService catService;

    private final CatOwnerService catOwnerService;

    private final CatTransferService catTransferService;

    private  CatOwnerDTO makambombich;

    private  CatOwnerDTO dverM;

    private  CatDTO shoka;

    private  CatDTO chillie;

    private  CatDTO taylor;

    private  ColorDTO whateverColor;

    @MockBean
    private Authentication authentication;

    @MockBean
    private SecurityContextHolder contextHolder;

    @MockBean
    private SecurityContext securityContext;

    @BeforeAll
    public void init(){
        MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class); // Мне было лень мокать и базу, чтобы это получились нормальные тесты, потому что почти вся бизнес логика у нас завязана на работе с БД, а не с сущностями.
        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
    }

    @BeforeEach
    public void setup(){
        whateverColor = colorService.create("white");
        Calendar calendar = Calendar.getInstance();

        calendar.set(2003, Calendar.JANUARY, 1);
        makambombich = catOwnerService.create("Roma",calendar.getTime(), "roma2003", "roma2003");
        calendar.set(2004, Calendar.FEBRUARY, 1);
        dverM = catOwnerService.create("Maha",calendar.getTime(), "maha", "maha");


        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(new com.blackburn.security.DefaultUserDetails("root", "root", Set.of(new SimpleGrantedAuthority("root"))));

        chillie = catService.create("Chillie", calendar.getTime(), "Just a cat", whateverColor.getColor(), makambombich.getId());
        shoka = catService.create("Shoka", calendar.getTime(), "Just a cat", whateverColor.getColor(), makambombich.getId());
        taylor = catService.create("Taylor", calendar.getTime(), "Just a cat", whateverColor.getColor(), dverM.getId());
    }



    @Autowired
    public TestCase(ColorService colorService, CatService catService, CatOwnerService catOwnerService, CatTransferService catTransferService){
        this.colorService = colorService;
        this.catOwnerService = catOwnerService;
        this.catService = catService;
        this.catTransferService = catTransferService;
    }

    @Test
    @Transactional
    public void Befriend_Test(){
        Mockito.when(authentication.getPrincipal()).thenReturn(new com.blackburn.security.DefaultUserDetails("roma2003", "roma2003", Set.of(new SimpleGrantedAuthority("owner"))));

        catService.addCatFriend(shoka.getId(), chillie.getId());
        catService.addCatFriend(shoka.getId(), taylor.getId());

        Assertions.assertThrows(FriendshipException.class, (() -> catService.addCatFriend(shoka.getId(), shoka.getId())));
        Assertions.assertEquals(catService.findById(shoka.getId()).getFriendCount(), 2);
    }

    @Test
    @Transactional
    public void Owner_Deletion_Test(){

        Befriend_Test();
        Cat_Transfer_Send_Request(); // проверить, что не осталось ссылок с других сущностей

        catOwnerService.delete(makambombich.getId());
        Assertions.assertThrows(SearchException.class, (() -> catOwnerService.findById(makambombich.getId())));
    }


    @Test
    @Transactional
    public void Cat_Transfer_Send_Request(){
        Mockito.when(authentication.getPrincipal()).thenReturn(new com.blackburn.security.DefaultUserDetails("roma2003", "roma2003", Set.of(new SimpleGrantedAuthority("owner"))));
        catTransferService.sendTransferRequest( dverM.getId(), makambombich.getId(), shoka.getId());
        Assertions.assertEquals(catTransferService.getOwnersTransferRequests(dverM.getId()).size(), 1);
    }


    @Test
    @Transactional
    public void Cat_Transfer_Accept_Request(){
        Cat_Transfer_Send_Request();
        Mockito.when(authentication.getPrincipal()).thenReturn(new com.blackburn.security.DefaultUserDetails("roma2003", "roma2003", Set.of(new SimpleGrantedAuthority("owner"))));
        List<TransferRequestDTO> requests = catTransferService.getOwnersTransferRequests(dverM.getId());
        Assertions.assertThrows(OwnershipException.class, (() -> catTransferService.acceptRequest(requests.get(0).getId())));
        Mockito.when(authentication.getPrincipal()).thenReturn(new com.blackburn.security.DefaultUserDetails("maha", "maha", Set.of(new SimpleGrantedAuthority("owner"))));
        catTransferService.acceptRequest(requests.get(0).getId());
        Assertions.assertEquals(catOwnerService.findById(dverM.getId()).getCats().size(), 2);
    }

}

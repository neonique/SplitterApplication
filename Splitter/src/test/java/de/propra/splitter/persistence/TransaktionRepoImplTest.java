package de.propra.splitter.persistence;

import static org.mockito.Mockito.mock;

import de.propra.splitter.service.ApplicationService;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJdbcTest
@ActiveProfiles("test")
@Sql("/createTable.sql")
public class TransaktionRepoImplTest {


  ApplicationService service= mock(ApplicationService.class);


}

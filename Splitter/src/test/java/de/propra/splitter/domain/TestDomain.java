package de.propra.splitter.domain;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TestDomain {

  @Test()
  @DisplayName("create group and add participants")
  void test_01(){
    User user = new User();
    User participant = new User();

    Group group = new Group(user);
    group.addUser(participant);

    assertThat(group.getUsers()).contains(participant, user);
  }
  @Test()
  @DisplayName("Create Transaction using Users")
  void test_02(){

  }

}

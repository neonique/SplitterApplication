package de.propra.splitter.services;

import de.propra.splitter.domain.Group;
import de.propra.splitter.domain.User;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TestGroupService {

  @Test
  @DisplayName("users group are found correct to user")
  void test_0(){

     User user1=new User("moaz");
     User user2=new User("nick");
     Set<Group> groups=new HashSet<>();
     Set<Group> userOneGroups=new HashSet<>();

    for (int i = 0; i <3 ; i++) {
      Group g=new Group(user1);
      groups.add(g);
      userOneGroups.add(g);
    }
      groups.add(new Group(user2));

    Set<Group> foundGroups=GroupService.userGroups(groups,user1);

    assertThat(userOneGroups).containsExactlyElementsOf(foundGroups);



  }
  @Test
  @DisplayName("")
  void test_1(){

  }


}

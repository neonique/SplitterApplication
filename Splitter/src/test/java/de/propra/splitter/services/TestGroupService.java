package de.propra.splitter.services;

import de.propra.splitter.domain.Group;
import de.propra.splitter.domain.User;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

public class TestGroupService {

  GroupService groupService = new GroupService();

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

    Set<Group> foundGroups=groupService.userGroups(groups,user1);

    assertThat(userOneGroups).containsExactlyElementsOf(foundGroups);



  }
  @Test
  @DisplayName("Accessing only closed groups")
  void test_1(){
    User user1=new User("moaz");
    Group group1 = new Group(user1);
    Group group2 = new Group(user1);

    group2.close();
    HashSet<Group> groups = new HashSet<>(Set.of(group1, group2));

    HashSet<Group> closed = groupService.closedUserGroups(groups, user1);
    assertThat(closed).containsExactly(group2);
  }

  @Test
  @DisplayName("Accessing only open groups")
  void test_2(){
    User user1=new User("moaz");
    Group group1 = new Group(user1);
    Group group2 = new Group(user1);

    group2.close();
    HashSet<Group> groups = new HashSet<>(Set.of(group1, group2));

    HashSet<Group> open = groupService.openUserGroups(groups, user1);
    assertThat(open).containsExactly(group1);
  }

}

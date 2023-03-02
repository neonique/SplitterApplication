package de.propra.splitter.services;

import de.propra.splitter.domain.Group;
import de.propra.splitter.domain.User;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

  public HashSet<Group> userGroups(Set<Group> groups, User user){
      return new HashSet<Group>(groups.stream().filter(group -> group.containsUser(user)).collect(Collectors.toSet()));
  }


  public HashSet<Group> openUserGroups(Set<Group> groups, User user){
      return new HashSet<Group>(groups.stream().filter(group -> group.containsUser(user)).filter(Predicate.not(Group::isClosed)).collect(Collectors.toSet()));
  }

   public HashSet<Group> closedUserGroups(Set<Group> groups, User user){
      return new HashSet<Group>(groups.stream().filter(group -> group.containsUser(user)).filter(Group::isClosed).collect(Collectors.toSet()));
  }

}

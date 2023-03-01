package de.propra.splitter.services;

import de.propra.splitter.domain.Group;
import de.propra.splitter.domain.User;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupService {

  public static HashSet<Group> userGroups(Set<Group> groups, User user){
   return new HashSet<Group>(groups.stream().filter(group -> group.containsUser(user)).collect(Collectors.toSet()));
  }

}

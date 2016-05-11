package nl.tudelft.thefirstorder.web.rest.mapper;

import nl.tudelft.thefirstorder.domain.Authority;
import nl.tudelft.thefirstorder.domain.User;
import nl.tudelft.thefirstorder.web.rest.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserMapper {

    UserDTO userToUserDto(User user);

    List<UserDTO> usersToUserDtos(List<User> users);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activationKey", ignore = true)
    @Mapping(target = "resetKey", ignore = true)
    @Mapping(target = "resetDate", ignore = true)
    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDTO userDto);

    List<User> userDtosToUsers(List<UserDTO> userDtos);

    /**
     * Creates new user from Id.
     * @param id Id
     * @return User
     */
    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    /**
     * Maps authorities to strings
     * @param authorities the authorities
     * @return the strings
     */
    default Set<String> stringsFromAuthorities(Set<Authority> authorities) {
        return authorities.stream().map(Authority::getName)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a set of authorities from a set of Strings.
     * @param strings Set of Strings
     * @return Set of authorities
     */
    default Set<Authority> authoritiesFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            Authority auth = new Authority();
            auth.setName(string);
            return auth;
        }).collect(Collectors.toSet());
    }
}

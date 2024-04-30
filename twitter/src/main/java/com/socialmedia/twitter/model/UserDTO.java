package com.socialmedia.twitter.model;

import com.socialmedia.twitter.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long user_id;
    private String name;
    private String email;

    public static List<UserDTO> mapToDTOList(List<User> users) {
        return users.stream()
                .map(UserDTO::mapToDTO)
                .collect(Collectors.toList());
    }

    public static UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUser_id(user.getUser_id());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}

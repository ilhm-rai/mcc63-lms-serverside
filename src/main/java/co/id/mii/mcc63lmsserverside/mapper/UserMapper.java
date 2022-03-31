package co.id.mii.mcc63lmsserverside.mapper;

import co.id.mii.mcc63lmsserverside.model.Dto.UserDto;
import co.id.mii.mcc63lmsserverside.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto userToUserDTO(User entity);

  User userDTOToUser(UserDto dto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateUserFromUserDTO(UserDto dto, @MappingTarget User entity);
}

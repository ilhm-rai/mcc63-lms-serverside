package co.id.mii.mcc63lmsserverside.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import co.id.mii.mcc63lmsserverside.model.Role;
import co.id.mii.mcc63lmsserverside.model.User;
import co.id.mii.mcc63lmsserverside.model.dto.request.CreateUser;
import co.id.mii.mcc63lmsserverside.model.dto.response.UserDataResponse;
import co.id.mii.mcc63lmsserverside.service.RoleService;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class ModelMapperConfig {

  private final RoleService roleService;

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    Converter<List<Long>, List<Role>> convertToRole = new Converter<List<Long>, List<Role>>() {
      public List<Role> convert(MappingContext<List<Long>, List<Role>> context) {
        return context.getSource()
            .stream()
            .map(id -> roleService.getById(id))
            .collect(Collectors.toList());
      }
    };

    Converter<List<Role>, List<String>> convertToRoleName =
        new Converter<List<Role>, List<String>>() {
          public List<String> convert(MappingContext<List<Role>, List<String>> context) {
            return context.getSource().stream()
                .map(role -> role.getName())
                .collect(Collectors.toList());
          }
        };

    PropertyMap<CreateUser, User> userDataMap = new PropertyMap<CreateUser, User>() {
      @Override
      protected void configure() {
        map().getProfile().setFullName(source.getFullName());
        using(convertToRole).map(source.getRolesId()).setRoles(new ArrayList<>());
      }
    };

    PropertyMap<User, UserDataResponse> userDataResponseMap =
        new PropertyMap<User, UserDataResponse>() {
          @Override
          protected void configure() {
            map().setFullName(source.getProfile().getFullName());
            using(convertToRoleName).map(source.getRoles()).setRole(new ArrayList<>());
          }
        };

    modelMapper.getConfiguration()
        .setAmbiguityIgnored(true)
        .setFieldMatchingEnabled(true)
        .setMatchingStrategy(MatchingStrategies.STRICT);

    modelMapper.addMappings(userDataMap);
    modelMapper.addMappings(userDataResponseMap);

    return modelMapper;
  }

}

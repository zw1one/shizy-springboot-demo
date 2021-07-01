package com.shizy.user.entity;

import com.shizy.utils.query.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author shizy
 * @since 2019-08-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @QueryParam(eq = true)
    private String userId;

    @QueryParam(eq = true)
    private String userAccount;

    @QueryParam(like = true)
    private String userName;

    private String nameAndAccount;

}

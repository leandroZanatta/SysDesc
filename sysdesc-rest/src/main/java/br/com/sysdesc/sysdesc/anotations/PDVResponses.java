package br.com.sysdesc.sysdesc.anotations;

import static br.com.sysdesc.util.constants.EndPointsConstants.STATUS_CODE_200;
import static br.com.sysdesc.util.constants.EndPointsConstants.STATUS_CODE_204;
import static br.com.sysdesc.util.constants.EndPointsConstants.STATUS_CODE_400;
import static br.com.sysdesc.util.constants.EndPointsConstants.STATUS_CODE_401;
import static br.com.sysdesc.util.constants.EndPointsConstants.STATUS_CODE_403;
import static br.com.sysdesc.util.constants.EndPointsConstants.STATUS_CODE_404;
import static br.com.sysdesc.util.constants.EndPointsConstants.STATUS_CODE_500;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = {

        @ApiResponse(code = 200, message = STATUS_CODE_200), @ApiResponse(code = 204, message = STATUS_CODE_204),
        @ApiResponse(code = 400, message = STATUS_CODE_400), @ApiResponse(code = 401, message = STATUS_CODE_401),
        @ApiResponse(code = 403, message = STATUS_CODE_403), @ApiResponse(code = 404, message = STATUS_CODE_404),
        @ApiResponse(code = 500, message = STATUS_CODE_500)

})
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PDVResponses {

}

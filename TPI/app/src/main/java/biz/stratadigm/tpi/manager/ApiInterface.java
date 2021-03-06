package biz.stratadigm.tpi.manager;

import biz.stratadigm.tpi.entity.dto.LoginDTO;
import biz.stratadigm.tpi.entity.dto.LoginResponseDTO;
import biz.stratadigm.tpi.entity.dto.RegisterDTO;
import biz.stratadigm.tpi.entity.dto.ThaliDTO;
import biz.stratadigm.tpi.entity.dto.VenueDTO;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import java.util.ArrayList;

/**
 * Created by vkiyako on 1/5/17.
 */

public interface ApiInterface {

    @POST("/auth_token")
    Observable<LoginResponseDTO> login(@Body LoginDTO loginDTO);

    @GET("/hello")
    Observable<Void> checkToken();

    @POST("/user")
    Observable<Void> registerUser(@Body RegisterDTO registerDTO);

    @GET("/venues")
    Observable<ArrayList<VenueDTO>> getVenues(@Query("offset") int offset);

    @GET("/thalis")
    Observable<ThaliDTO> getThalis(@Query("offset") int offset);
}

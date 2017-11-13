package com.kelvin.jacksgogo.Models;

import com.kelvin.jacksgogo.Models.Base.Global;
import com.kelvin.jacksgogo.Models.Base.JGGBaseModel;
import com.kelvin.jacksgogo.Models.User.JGGUserBaseModel;

/**
 * Created by PUMA on 11/3/2017.
 */

public class JGGBiddingProviderModel extends JGGBaseModel {

    private JGGUserBaseModel user;
    private Double price = 0.0;
    private Global.BiddingStatus status = Global.BiddingStatus.PENDING;
    private boolean isNew = false;
}

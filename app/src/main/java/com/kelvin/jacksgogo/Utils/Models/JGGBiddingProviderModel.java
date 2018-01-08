package com.kelvin.jacksgogo.Utils.Models;

import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Base.JGGBaseModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;

/**
 * Created by PUMA on 11/3/2017.
 */

public class JGGBiddingProviderModel extends JGGBaseModel {

    private JGGUserBaseModel user;
    private Float price = 0.0f;
    private Global.BiddingStatus status;

    public JGGBiddingProviderModel() {
        super();

        init();
    }

    public void init() {
        status = Global.BiddingStatus.PENDING;
    }

    public JGGUserBaseModel getUser() {
        return user;
    }

    public void setUser(JGGUserBaseModel user) {
        this.user = user;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Global.BiddingStatus getStatus() {
        return status;
    }

    public void setStatus(Global.BiddingStatus status) {
        this.status = status;
    }
}

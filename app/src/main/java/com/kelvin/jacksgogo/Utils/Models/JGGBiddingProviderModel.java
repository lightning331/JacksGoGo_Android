package com.kelvin.jacksgogo.Utils.Models;

import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Global.BiddingStatus;
import com.kelvin.jacksgogo.Utils.Models.Base.JGGBaseModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserBaseModel;

/**
 * Created by PUMA on 11/3/2017.
 */

public class JGGBiddingProviderModel extends JGGBaseModel {

    private JGGUserBaseModel user;
    private Float price = 0.0f;
    private BiddingStatus status;
    private int messageCount;

    public JGGBiddingProviderModel() {
        super();

        init();
    }

    public void init() {
        status = BiddingStatus.PENDING;
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

    public BiddingStatus getStatus() {
        return status;
    }

    public void setStatus(BiddingStatus status) {
        this.status = status;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }
}

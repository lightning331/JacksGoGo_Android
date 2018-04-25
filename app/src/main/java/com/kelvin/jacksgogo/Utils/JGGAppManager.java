package com.kelvin.jacksgogo.Utils;

import android.content.Context;

import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoclubModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGQuotationModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGRegionModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import java.util.ArrayList;

/**
 * Created by PUMA on 1/6/2018.
 */

public class JGGAppManager {

    private static JGGAppManager appManager = null;

    /*
     *  User
     */
    private JGGUserProfileModel currentUser;
    private ArrayList<JGGCategoryModel> categories;
    private ArrayList<JGGRegionModel> regions;
    private JGGRegionModel currentRegion;

    /**
     *  Appointment
     */
    private JGGCategoryModel selectedCategory;
    private JGGAppointmentModel selectedAppointment;
    private JGGQuotationModel selectedQuotation;
    private JGGGoclubModel goclubModel;
    private JGGProposalModel selectedProposal;

    private JGGAppManager() {

    }

    public static JGGAppManager getInstance() {
        if(null == appManager){
            appManager = new JGGAppManager();
        }
        return appManager;
    }

    public JGGUserProfileModel getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(JGGUserProfileModel currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<JGGCategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<JGGCategoryModel> categories) {
        this.categories = categories;
    }

    public ArrayList<JGGRegionModel> getRegions() {
        return regions;
    }

    public void setRegions(ArrayList<JGGRegionModel> regions) {
        this.regions = regions;
    }

    public JGGRegionModel getCurrentRegion() {
        currentRegion = regions.get(0);
        return currentRegion;
    }

    public void setCurrentRegion(JGGRegionModel region) {
        this.currentRegion = region;
    }

    public JGGCategoryModel getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(JGGCategoryModel selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public JGGAppointmentModel getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(JGGAppointmentModel selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }

    public JGGQuotationModel getSelectedQuotation() {
        return selectedQuotation;
    }

    public void setSelectedQuotation(JGGQuotationModel selectedQuotation) {
        this.selectedQuotation = selectedQuotation;
    }

    public JGGProposalModel getSelectedProposal() {
        return selectedProposal;
    }

    public void setSelectedProposal(JGGProposalModel selectedProposal) {
        this.selectedProposal = selectedProposal;
    }

    public JGGGoclubModel getGoclubModel() {
        return goclubModel;
    }

    public void setGoclubModel(JGGGoclubModel goclubModel) {
        this.goclubModel = goclubModel;
    }
}

package com.kelvin.jacksgogo.Utils;

import android.content.Context;

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

    private static JGGAppManager appManager = new JGGAppManager();

    /*
     *  User
     */
    public static JGGUserProfileModel currentUser;
    public static ArrayList<JGGCategoryModel> categories;
    public static ArrayList<JGGRegionModel> regions;
    public static JGGRegionModel currentRegion;

    /*
     *  Appointment
     */
    public static JGGCategoryModel selectedCategory;
    public static JGGAppointmentModel selectedAppointment;
    public static JGGQuotationModel selectedQuotation;

    /*
     *  Proposal
     */
    public static JGGProposalModel selectedProposal;

    private JGGAppManager() {

    }

    public static JGGAppManager getInstance(Context context) {
        return appManager;
    }

    public JGGRegionModel getCurrentRegion() {
        currentRegion = regions.get(0);
        return currentRegion;
    }

    public void setCurrentRegion(JGGRegionModel region) {
        this.currentRegion = region;
    }

    public static JGGCategoryModel getSelectedCategory() {
        return selectedCategory;
    }

    public static void setSelectedCategory(JGGCategoryModel category) {
        selectedCategory = category;
    }

    public static JGGAppointmentModel getSelectedAppointment() {
        return selectedAppointment;
    }

    public static void setSelectedAppointment(JGGAppointmentModel appointment) {
        selectedAppointment = appointment;
    }

    public static JGGProposalModel getSelectedProposal() {
        return selectedProposal;
    }

    public static void setSelectedProposal(JGGProposalModel proposal) {
        selectedProposal = proposal;
    }

    public static void clearAll() {
        currentUser = null;
    }
}

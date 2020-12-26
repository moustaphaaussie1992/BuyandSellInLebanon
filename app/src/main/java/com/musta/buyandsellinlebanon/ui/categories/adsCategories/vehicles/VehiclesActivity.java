package com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.musta.buyandsellinlebanon.R;
import com.musta.buyandsellinlebanon.ads.models.CreateAdTypeModel;
import com.musta.buyandsellinlebanon.utils.Constants;
import com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.adapters.AdTypeTitleRecyclerViewAdapter;
import com.musta.buyandsellinlebanon.ui.categories.adsCategories.vehicles.models.AdTypeTitleModel;

import java.util.Arrays;
import java.util.List;

import static com.musta.buyandsellinlebanon.utils.Constants.Planting_and_Food;
import static com.musta.buyandsellinlebanon.utils.Constants.Services;

public class VehiclesActivity extends AppCompatActivity {

    RecyclerView vehiclesRecyclerView;
    List<AdTypeTitleModel> AdTypeTitleModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);


        vehiclesRecyclerView = findViewById(R.id.vehiclesRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        vehiclesRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        vehiclesRecyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        if (intent.hasExtra("adTypeTitle")) {
            String adTypeTitle = intent.getStringExtra("adTypeTitle");
            if (adTypeTitle.equals(Constants.VEHICLES)) {
                setTitle(getString(R.string.vehicles));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.cars_for_sale), Constants.CARS_FOR_SALE),
                        new AdTypeTitleModel(getString(R.string.cars_for_rent), Constants.CARS_FOR_RENT),
                        new AdTypeTitleModel(getString(R.string.vehicles_accessories), Constants.TYPE_VEHICLES_ACCESSORIES),
                        new AdTypeTitleModel(getString(R.string.spare_parts), Constants.TYPE_VEHICLES_SPARE_PARTS),
                        new AdTypeTitleModel(getString(R.string.number_plates), Constants.TYPE_VEHICLES_NUMBER_PLATES),
                        new AdTypeTitleModel(getString(R.string.motorcycles), Constants.TYPE_VEHICLES_MOTORCYCLES),
                        new AdTypeTitleModel(getString(R.string.trucks_and_buses), Constants.TYPE_VEHICLES_TRUCKS_AND_BUSES),
                        new AdTypeTitleModel(getString(R.string.boats), Constants.TYPE_VEHICLES_BOATS),
                        new AdTypeTitleModel(getString(R.string.others_vehicles), Constants.TYPE_OTHERS_VEHICLES)
                );
            } else if (adTypeTitle.equals(Constants.PROPERTIES)) {
                setTitle(getString(R.string.properties));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.for_sale_apartments_and_villas), Constants.TYPE_FOR_SALE_APARTMENTS_AND_VILLAS),
                        new AdTypeTitleModel(getString(R.string.for_rent_apartments_and_villas), Constants.TYPE_FOR_RENT_APARTMENTS_AND_VILLAS),
                        new AdTypeTitleModel(getString(R.string.for_sale_commercial), Constants.TYPE_FOR_SALE_COMMERCIAL),
                        new AdTypeTitleModel(getString(R.string.for_rent_commercial), Constants.TYPE_FOR_RENT_COMMERCIAL),
                        new AdTypeTitleModel(getString(R.string.lands), Constants.TYPE_LANDS),
                        new AdTypeTitleModel(getString(R.string.chalets_and_cabins), Constants.TYPE_CHALETS_AND_CABINS),
                        new AdTypeTitleModel(getString(R.string.buildings_and_multiple_units), Constants.TYPE_BUILDINGS_AND_MULTIPLE_UNITS),
                        new AdTypeTitleModel(getString(R.string.rental_wanted), Constants.TYPE_RENTAL_WANTED),
                        new AdTypeTitleModel(getString(R.string.rooms_for_rent), Constants.TYPE_ROOMS_FOR_RENT)
                );
            } else if (adTypeTitle.equals(Constants.MOBILES)) {
                setTitle(getString(R.string.mobile_phones_and_accessories));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.mobile_phones), Constants.TYPE_MOBILE_PHONES),
                        new AdTypeTitleModel(getString(R.string.mobile_accessories), Constants.TYPE_MOBILE_ACCESSORIES),
                        new AdTypeTitleModel(getString(R.string.mobile_numbers), Constants.TYPE_MOBILE_NUMBERS),
                        new AdTypeTitleModel(getString(R.string.smart_watches), Constants.TYPE_MOBILE_WATCHES)
                );
            } else if (adTypeTitle.equals(Constants.ELECTRONICS_AND_HOME_APPLIANCES)) {
                setTitle(getString(R.string.electronics_and_home_appliances));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.tv_and_Video), Constants.TYPE_TV_and_Video),
                        new AdTypeTitleModel(getString(R.string.home_audio_and_speakers), Constants.TYPE_Home_Audio_and_Speakers),
                        new AdTypeTitleModel(getString(R.string.kitchen_equipment_and_appliances), Constants.TYPE_Kitchen_Equipment_and_Appliances),
                        new AdTypeTitleModel(getString(R.string.AC_Cooling_and_Heating), Constants.TYPE_ac_Cooling_and_Heating),
                        new AdTypeTitleModel(getString(R.string.cleaning_appliances), Constants.TYPE_Cleaning_Appliances),
                        new AdTypeTitleModel(getString(R.string.washing_machines_and_dryers), Constants.TYPE_Washing_Machines_and_Dryers),
                        new AdTypeTitleModel(getString(R.string.laptops_tablets_computers), Constants.TYPE_Laptops_Tablets_Computers),
                        new AdTypeTitleModel(getString(R.string.computer_parts_and_it_accessories), Constants.TYPE_Computer_Parts_and_IT_Accessories),
                        new AdTypeTitleModel(getString(R.string.cameras), Constants.TYPE_Cameras),
                        new AdTypeTitleModel(getString(R.string.gaming_consoles_and_accessories), Constants.TYPE_Gaming_Consoles_and_Accessories),
                        new AdTypeTitleModel(getString(R.string.video_games), Constants.TYPE_Video_Games),
                        new AdTypeTitleModel(getString(R.string.other_home_appliances), Constants.TYPE_Other_Home_Appliances)

                );
            } else if (adTypeTitle.equals(Constants.HOME_FURNITURE_AND_DECOR)) {
                setTitle(getString(R.string.home_furniture_and_decor));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.living_room), Constants.Living_Room),
                        new AdTypeTitleModel(getString(R.string.bedroom), Constants.Bedroom),
                        new AdTypeTitleModel(getString(R.string.dining_room), Constants.Dining_Room),
                        new AdTypeTitleModel(getString(R.string.kitchen_and_kitchenware), Constants.Kitchen_and_Kitchenware),
                        new AdTypeTitleModel(getString(R.string.bathroom), Constants.Bathroom),
                        new AdTypeTitleModel(getString(R.string.home_decoration_and_accessories), Constants.Home_Decoration_and_Accessories),
                        new AdTypeTitleModel(getString(R.string.other_home_furniture_and_decor), Constants.Other_Home_Furniture_and_Decor)
                );
            } else if (adTypeTitle.equals(Constants.FASHION_AND_BEAUTY)) {
                setTitle(getString(R.string.fashion_and_beauty));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.clothing_for_men), Constants.Clothing_for_Men),
                        new AdTypeTitleModel(getString(R.string.accessories_for_men), Constants.Accessories_for_Men),
                        new AdTypeTitleModel(getString(R.string.clothing_for_women), Constants.Clothing_for_Women),
                        new AdTypeTitleModel(getString(R.string.accessories_for_women), Constants.Accessories_for_Women),
                        new AdTypeTitleModel(getString(R.string.makeup_and_cosmetics), Constants.Makeup_and_Cosmetics),
                        new AdTypeTitleModel(getString(R.string.jewelry_and_faux_bijoux), Constants.Jewelry_and_FauxBijoux),
                        new AdTypeTitleModel(getString(R.string.watches), Constants.Watches),
                        new AdTypeTitleModel(getString(R.string.luggage), Constants.Luggage)

                );
            } else if (adTypeTitle.equals(Constants.PETS)) {
                setTitle(getString(R.string.pets));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.animal_and_pet_accessories), Constants.Animal_and_Pet_accessories),
                        new AdTypeTitleModel(getString(R.string.dogs), Constants.Dogs),
                        new AdTypeTitleModel(getString(R.string.cats), Constants.Cats),
                        new AdTypeTitleModel(getString(R.string.birds), Constants.Birds),
                        new AdTypeTitleModel(getString(R.string.livestock), Constants.Livestock),
                        new AdTypeTitleModel(getString(R.string.horses), Constants.Horses),
                        new AdTypeTitleModel(getString(R.string.fish), Constants.Fish),
                        new AdTypeTitleModel(getString(R.string.other_animals_and_pets), Constants.Other_Animals_and_Pets)
                );
            } else if (adTypeTitle.equals(Constants.KIDS_AND_BABIES)) {
                setTitle(getString(R.string.kids_and_babies));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.toys_for_kids), Constants.Toys_for_kids),
                        new AdTypeTitleModel(getString(R.string.strollers_and_seats), Constants.Strollers_and_Seats),
                        new AdTypeTitleModel(getString(R.string.kids_and_babies_clothing), Constants.Kids_and_Babies_Clothing),
                        new AdTypeTitleModel(getString(R.string.cribs_and_bedroom_furniture), Constants.Cribs_and_Bedroom_Furniture),
                        new AdTypeTitleModel(getString(R.string.bathing_accessories), Constants.Bathing_Accessories),
                        new AdTypeTitleModel(getString(R.string.feeding_and_nursing), Constants.Feeding_and_Nursing),
                        new AdTypeTitleModel(getString(R.string.safety_and_monitors), Constants.Safety_and_Monitors),
                        new AdTypeTitleModel(getString(R.string.other_for_kids_and_babies), Constants.Other_for_Kids_and_Babies)

                );
            } else if (adTypeTitle.equals(Constants.Sports_and_Equipments)) {
                setTitle(getString(R.string.sports_and_equipments));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.bicycles_and_accessories), Constants.Bicycles_and_Accessories),
                        new AdTypeTitleModel(getString(R.string.outdoors_and_camping), Constants.Outdoors_and_Camping),
                        new AdTypeTitleModel(getString(R.string.gym_fitness_and_fighting_sports), Constants.Gym_Fitness_and_Fighting_sports),
                        new AdTypeTitleModel(getString(R.string.ball_sports), Constants.Ball_Sports),
                        new AdTypeTitleModel(getString(R.string.billiard_and_similar_games), Constants.Billiard_and_Similar_Games),
                        new AdTypeTitleModel(getString(R.string.ski_and_winter_sports), Constants.Ski_and_Winter_Sports),
                        new AdTypeTitleModel(getString(R.string.water_sports_and_diving), Constants.Water_Sports_and_Diving),
                        new AdTypeTitleModel(getString(R.string.tennis_and_racket_sports), Constants.Tennis_and_Racket_Sports),
                        new AdTypeTitleModel(getString(R.string.other_sports), Constants.Other_Sports)


                );
            } else if (adTypeTitle.equals(Constants.Hobbies_Music_Art_and_Books)) {
                setTitle(getString(R.string.hobbies_music_art_and_books));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.antiques_and_collectibles), Constants.Antiques_and_Collectibles),
                        new AdTypeTitleModel(getString(R.string.musical_instruments), Constants.Musical_Instruments),
                        new AdTypeTitleModel(getString(R.string.books), Constants.Books),
                        new AdTypeTitleModel(getString(R.string.movies_and_music), Constants.Movies_and_Music),
                        new AdTypeTitleModel(getString(R.string.games_and_hobbies), Constants.Games_and_Hobbies),
                        new AdTypeTitleModel(getString(R.string.tickets_and_vouchers), Constants.Tickets_and_Vouchers),
                        new AdTypeTitleModel(getString(R.string.stationery_and_study_tools), Constants.Stationery_and_Study_Tools),
                        new AdTypeTitleModel(getString(R.string.other_items), Constants.Other_Items)
                );
            } else if (adTypeTitle.equals(Constants.Jobs)) {
                setTitle(getString(R.string.jobs));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.job_seekers), Constants.Job_Seekers),
                        new AdTypeTitleModel(getString(R.string.jobs_available), Constants.Jobs_Available)

                );
            } else if (adTypeTitle.equals(Constants.Business_and_Industrial)) {
                setTitle(getString(R.string.business_and_industrial));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.restaurants_equipment), Constants.Restaurants_Equipment),
                        new AdTypeTitleModel(getString(R.string.industrial_and_construction_equipment), Constants.Industrial_and_Construction_Equipment),
                        new AdTypeTitleModel(getString(R.string.medical_and_wellbeing_equipment), Constants.Medical_and_Wellbeing_Equipment),
                        new AdTypeTitleModel(getString(R.string.retail_and_shop_equipement), Constants.Retail_and_Shop_Equipement),
                        new AdTypeTitleModel(getString(R.string.business_opportunites_and_shops_liquidation), Constants.Business_Opportunites_and_Shops_Liquidation),
                        new AdTypeTitleModel(getString(R.string.other_business_and_industrial), Constants.Other_Business_and_Industrial)

                );
            } else if (adTypeTitle.equals(Services)) {
                setTitle(getString(R.string.services));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.home_improvements_and_repair), Constants.Home_Improvements_and_Repair),
                        new AdTypeTitleModel(getString(R.string.personal_services), Constants.Personal_Services),
                        new AdTypeTitleModel(getString(R.string.corporate_services), Constants.Corporate_Services),
                        new AdTypeTitleModel(getString(R.string.vehicle_repair_services), Constants.Vehicle_Repair_Services),
                        new AdTypeTitleModel(getString(R.string.transportation_and_logistics_services), Constants.Transportation_and_logistics_Services),
                        new AdTypeTitleModel(getString(R.string.iT_design_and_printing_services), Constants.IT_Design_and_Printing_Services),
                        new AdTypeTitleModel(getString(R.string.other_services), Constants.Other_Services)
                );
            } else if (adTypeTitle.equals(Planting_and_Food)) {
                setTitle(getString(R.string.planting_and_food));
                AdTypeTitleModels = Arrays.asList(
                        new AdTypeTitleModel(getString(R.string.garden), Constants.Garden),
                        new AdTypeTitleModel(getString(R.string.food), Constants.Food)
                );
            } else {
                finish();
            }
        }


        AdTypeTitleRecyclerViewAdapter vehiclesRecyclerViewAdapter = new AdTypeTitleRecyclerViewAdapter(AdTypeTitleModels, this);
        vehiclesRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        vehiclesRecyclerView.setAdapter(vehiclesRecyclerViewAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}

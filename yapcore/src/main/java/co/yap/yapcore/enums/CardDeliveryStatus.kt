package co.yap.yapcore.enums

import androidx.annotation.Keep

@Keep
enum class CardDeliveryStatus {
    BOOKED, SHIPPING, SHIPPED, FAILED, ORDERED;
}
//
//PRE_BOOKED                          (1,  "PreBooked",                          "BOOKED"),
//ALLOTED_FOR_PICKUP                  (2,  "Allotted For Pickup",                "SHIPPING"),
//PACKAGE_PICKED_UP                   (3,  "Package Picked-Up",                  "SHIPPING"),
//PACKAGE_IN_STORE                    (4,  "Package in-store",                   "SHIPPING"),
//BOOKING_CONFIRMED                   (5,  "Booking Confirmed",                  "SHIPPING"),
//ASSIGNED_FOR_DELIVERY               (6,  "Assigned for Delivery",              "SHIPPING"),
//HANDOVER_TO_SUPERVISOR_FOR_DELIVERY (7,  "Handover to Supervisor for Delivery","SHIPPING"),
//HANDOVER_TO_COURIER_FOR_DELIVERY    (8,  "Handover to Courier for Delivery",   "SHIPPING"),
//RECEIVED_BY_SUPERVISOR              (9,  "Received by Supervisor",             "SHIPPING"),
//ON_THE_WAY_TO_DELIVERY              (10, "On The Way to Delivery",             "SHIPPING"),
//DELIVERED_SUCCESS                   (11, "Delivered Success",                  "SHIPPED"),
//DELIVERED_FAILED                    (12, "Delivery Failed",                    "FAILED"),
//RETURNED_TO_STORE                   (13, "Returned to Store",                  "FAILED"),
//ASSIGNED_TO_RE_DELIVERY             (14, "Assigned for Re-Delivery",           "SHIPPING"),
//RETURNED_TO_PARENT                  (15, "Returned to Parent",                 "FAILED"),
//CANCELLED                           (16, "Cancelled",                          "FAILED");

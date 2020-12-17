package com.example.courierapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Routes {

   // private Bounds bounds;
    private String copyrights;
    //private PolyLine overviewPolyline;
    private String summary;
    @SerializedName("legs")
    private List<Legs> legs;
    private List<?> warnings;
    private List<Integer> waypointOrder;

 public String getCopyrights() {
  return copyrights;
 }

 public void setCopyrights(String copyrights) {
  this.copyrights = copyrights;
 }

 public String getSummary() {
  return summary;
 }

 public void setSummary(String summary) {
  this.summary = summary;
 }

 public List<Legs> getLegs() {
  return legs;
 }

 public void setLegs(List<Legs> legs) {
  this.legs = legs;
 }

 public List<?> getWarnings() {
  return warnings;
 }

 public void setWarnings(List<?> warnings) {
  this.warnings = warnings;
 }

 public List<Integer> getWaypointOrder() {
  return waypointOrder;
 }

 public void setWaypointOrder(List<Integer> waypointOrder) {
  this.waypointOrder = waypointOrder;
 }
}

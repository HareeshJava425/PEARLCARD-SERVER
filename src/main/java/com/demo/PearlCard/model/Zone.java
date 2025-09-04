package com.demo.PearlCard.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Zone {

    private int zoneNumber;

    private List<Station> stations;

}

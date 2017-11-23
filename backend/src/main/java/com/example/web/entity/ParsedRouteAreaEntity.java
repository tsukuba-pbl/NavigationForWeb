package com.example.web.entity;

import lombok.Data;

@Data
public class ParsedRouteAreaEntity {
	private RouteEntity routeEntity;			//ルート情報
	private AreaEntity[] areaEntityArray;	//エリア情報
}

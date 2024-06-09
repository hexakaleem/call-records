package com.livinservices.ProjectBoilerPlate.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SoftwareWebhook
{
	//
	//{
	//                     "phone": str_current_clip,
	//                     "user": 1
	//                }
	//

	private String phone;
	private Long user;

}

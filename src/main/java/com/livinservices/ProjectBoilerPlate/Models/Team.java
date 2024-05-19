package com.livinservices.ProjectBoilerPlate.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="teams")
public class Team implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String name;


	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
			name="team_users",
			joinColumns={@JoinColumn(name="TEAM_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="MEMBER_ID", referencedColumnName="ID")})
	private List<User> users;

}


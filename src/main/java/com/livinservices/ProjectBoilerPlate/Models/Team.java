package com.livinservices.ProjectBoilerPlate.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
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
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(nullable=false)
	private String name;

	@ManyToOne  // One team belongs to one organization
	@JoinColumn(name = "organization_id")
	private Organization organization;

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
			name="team_users",
			joinColumns={@JoinColumn(name="TEAM_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="MEMBER_ID", referencedColumnName="ID")})
	private List<User> users;

}


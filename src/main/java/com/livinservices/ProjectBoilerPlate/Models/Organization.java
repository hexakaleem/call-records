package com.livinservices.ProjectBoilerPlate.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="organizations")
public class Organization implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable=false)
	private String name;

	@OneToOne
	@JoinColumn(name = "created_by")
	private User created_by;

	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
			name="team_organization_association",
			joinColumns={@JoinColumn(name="ORGANIZATION_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="TEAM_ID", referencedColumnName="ID")})
	private List<Team> teams = new ArrayList<>();

}
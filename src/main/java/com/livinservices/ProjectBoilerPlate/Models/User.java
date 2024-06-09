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
@Table(name="users")
public class User implements Serializable
{
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private String userName;

	@Column(nullable=false, unique=true)
	private String email;

	@Column(nullable=false)
	private String password;

	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	@ManyToOne
	@JoinColumn(name="created_by_id")
	private User createdBy;

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
			name="users_roles",
			joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
	private List<Role> roles = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
			name="team_users",
			joinColumns={@JoinColumn(name="MEMBER_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="TEAM_ID", referencedColumnName="ID")})
	private List<Team> teams = new ArrayList<>();

	@OneToMany(mappedBy = "madeBy")  // MappedBy for one-to-many
	private List<Call> calls;



}
package com.compasso.uol.gabriel.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "city")
@Entity(name = "city")
public class City implements Serializable {
	private static final long serialVersionUID = 3259874520308167531L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	@Size(min = 1, max = 70, message = "O campo 'Nome' deve conter entre 1 e 70 caracteres.")
	private String name;

	@Column(name = "state", nullable = false)
	@Size(min = 1, max = 20, message = "O campo 'Estado' deve conter entre 1 e 20 caracteres.")
	private String state;

	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Client> clients;

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", state=" + state + ", clients=" + clients + "]";
	}
}

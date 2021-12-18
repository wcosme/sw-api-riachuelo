package br.com.riachuelo.api.starwars.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name="TB_FILM")
public class Film implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="episode_id")
	private Integer episodeId;
	
	@Column(name="opening_crawl", length = 4000)
	private String openingCrawl;
	
	@Column(name="director")
	private String director;
	
	@Column(name="producer")
	private String producer;
	
	@Column(name="release_date")
	private LocalDate releaseDate;
	
	@Column(name="url")
	private String url;

}

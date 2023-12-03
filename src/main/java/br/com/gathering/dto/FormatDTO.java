package br.com.gathering.dto;

import br.com.gathering.entity.Format;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormatDTO {

//	Not needed in post method
//	private Long id;

    private String name;

    private Integer lifeCount;

    public Format toFormat() {
    	Format format = new Format();
    	format.setName(this.name);
    	format.setLifeCount(this.lifeCount);

    	return format;
    }

    @Override
    public String toString() {
		return "FormatDTO: {\n"
//				+ "	id: " + this.id + ",\n"
				+ "	name: " + this.name + ",\n"
				+ "	lifeCount: " + this.lifeCount + ",\n"
				+ "}";
    }

}

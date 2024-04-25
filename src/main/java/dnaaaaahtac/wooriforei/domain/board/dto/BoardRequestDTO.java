package dnaaaaahtac.wooriforei.domain.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDTO {

    @NotEmpty
    String title;

    @NotEmpty
    String content;
}

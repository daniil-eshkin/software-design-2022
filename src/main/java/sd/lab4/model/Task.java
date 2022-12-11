package sd.lab4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    private long id;
    private String name;
    private String summary;
    private boolean completed;
    private Timestamp creationTime;
    private Timestamp completionTime;
}

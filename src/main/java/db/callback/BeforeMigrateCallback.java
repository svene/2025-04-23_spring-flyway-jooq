package db.callback;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
public class BeforeMigrateCallback implements Callback {
	@Override
	public boolean supports(Event event, Context context) {
		return event == Event.BEFORE_MIGRATE;
	}

	@Override
	public boolean canHandleInTransaction(Event event, Context context) {
		return true;
	}

	@Override
	public void handle(Event event, Context context) {
		try (Statement stmt = context.getConnection().createStatement()) {
			log.info("🧹 Truncating tables before migration...");
			stmt.execute("TRUNCATE TABLE flyway_schema_history;");
			List<String> tables = List.of("person");
			for (String table : tables) {
				stmt.execute("DROP TABLE IF EXISTS %s;".formatted(table));
			}
			context.getConnection().commit();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to truncate tables before migration", e);
		}
		log.info("===== BeforeMigrateCallback.handle");
	}

	@Override
	public String getCallbackName() {
		return "afterMigrate";
	}
}

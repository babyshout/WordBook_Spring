package kopo.data.wordbook.app.dto;

public class CommonData {
    public CommonData() {
    }

    public boolean validBody(String... args) {

        for (String arg : args) {
            if (arg == null || arg.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
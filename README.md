# The First Order

## Development
Before you can develop on this project, you need the following dependencies:

1. NodeJS
2. Gulp - install with `npm install -g gulp`
3. Yeoman - install with `npm install -g yeoman`
4. Bower - install with `npm install -g bower`

The project is configured with the Gradle wrapper. Run `./gradlew bootRun` to run the backend.

Hot reloading for Java is configured. Recompile the changed classes manually, if your IDE doesn't do this for you.
For the frontend, run Gulp to enable hot reloading.

## Building for production

To optimize the thefirstorder client for production, run:

    ./gradlew -Pprod clean bootRepackage

This will concatenate and minify CSS and JavaScript files. It will also modify `index.html` so it references
these new files.

To ensure everything worked, run:

    java -jar build/libs/*.war --spring.profiles.active=prod

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

## Testing

Unit tests for Javascript are run by [Karma][] and written with [Jasmine][]. They're located in `src/test/javascript/` and can be run with:

    gulp test

Unit tests for Java can be run with:

    ./gradlew test


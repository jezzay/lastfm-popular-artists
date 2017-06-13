import React, {Component} from "react";

class ArtistGeoLookup extends Component {
    constructor() {
        super();
        this.state = {country: ''};
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const name = event.target.name;
        this.setState({
            [name]: event.target.value
        });
    }

    handleSubmit(event) {
        console.log('submitted: ', this.state);
        event.preventDefault();
    }

    render() {
        return (
            <div className="ArtistGeoLookup">
                <form onSubmit={this.handleSubmit}>
                    <p>Please enter a ISO 3166-1 country name</p>
                    <label form="country">
                        Country
                        <input name="country" type="text" value={this.state.country} onChange={this.handleChange}/>
                    </label>
                    <p>
                        <button className="geo-submit" onClick={() => this.setState({value: 'X'})}>
                            Submit
                        </button>
                    </p>
                </form>

            </div>
        );
    }
}

export default ArtistGeoLookup;

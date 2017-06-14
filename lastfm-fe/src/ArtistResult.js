import React, {Component} from "react";

class ArtistResult extends Component {

    render() {
        return (
            <div>
                <div>
                    Name: {this.props.name}
                    <br/>
                    <img src={this.props.imageURL} alt=""/>
                </div>
            </div>
        )
    }
}

export default ArtistResult;
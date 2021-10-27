import { IImageMap } from "./imagemap";
import { IPost } from "./post";

export interface IUserAccount {
    userId: number;
    username: string;
    password: string;
    userEmail: string;
    firstName: string;
    lastName: string;
    birthday: Date;
    statusText: string;
    profilePicture: IImageMap;
    postList: IPost[];
    dittoFollowerList: IUserAccount[];
    dittoFollowingList: IUserAccount[];
}
